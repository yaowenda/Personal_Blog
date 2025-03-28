package com.example.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.NotFoundException;
import com.example.blog.dao.TypeMapper;
import com.example.blog.entity.Blog;
import com.example.blog.dao.BlogMapper;



import com.example.blog.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaowenda
 * @since 2025-02-11
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeMapper typeMapper;

    @Value("${upload.path:/home/blog/uploads}")  // 修改默认路径
    private String uploadPath;
    @Autowired
    private MarkdownUtils markdownUtils;

    private Path getUploadPath() throws IOException {
        Path path = Paths.get(uploadPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        return path;
    }

    private String processMarkdownImages(String markdown, Path markdownFilePath) throws IOException {
        // 使用配置的上传路径
        Path uploadDir = getUploadPath();

        Pattern pattern = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(markdown);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String altText = matcher.group(1);
            String imageUrl = matcher.group(2);
            if (imageUrl.startsWith("http")) {
                matcher.appendReplacement(result, matcher.group(0));
                continue;
            }

            if (!imageUrl.startsWith("http") && !imageUrl.startsWith("/uploads/")) {
                Path imagePath = markdownFilePath.getParent().resolve(imageUrl);

                if (Files.exists(imagePath)) {
                    String extension = FilenameUtils.getExtension(imageUrl);
                    String newFilename = UUID.randomUUID().toString() + "." + extension;

                    // 使用配置的上传路径
                    Path targetPath = uploadDir.resolve(newFilename);
                    Files.copy(imagePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                    imageUrl = "/uploads/" + newFilename;
                }
            }

            // 替换为新的图片标记
            matcher.appendReplacement(result, "![" + altText + "](" + imageUrl + ")");
        }
        matcher.appendTail(result);
        return result.toString();

    }

    @Override
    public Blog createFromMarkdown(MultipartFile file, Blog blog) throws IOException {
        // 先保存Markdown文件到临时目录
        Path tempDir = Files.createTempDirectory("markdown_");
        Path tempFile = tempDir.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

//        String fileName = file.getOriginalFilename();
        String markdownContent = new String(file.getBytes(), StandardCharsets.UTF_8);

        // 处理Markdown中的图片，传入Markdown文件的路径以便处理同目录下的图片
        markdownContent = processMarkdownImages(markdownContent, tempFile);

        // 使用文件名作为标题（去掉扩展名）
//        String title = FilenameUtils.removeExtension(fileName);

//        // 从Markdown内容中提取标签（查找类似 "tags: tag1, tag2" 的行）
//        String tags = null;
//        String[] lines = markdownContent.split("\\r?\\n");
//        for (String line : lines) {
//            if (line.toLowerCase().startsWith("tags:")) {
//                tags = line.substring(5).trim();
//                break;
//            }
//        }

        // 转换Markdown为HTML
//        String htmlContent = MarkdownUtils.markdownToHtmlExtensions(markdownContent);

        // 创建文章

//        blog.setTitle(title);
        blog.setContent(markdownContent);
//        blog.setTopicId(topicId);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);


        // 清理临时目录
        Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

        return blog;
    }


    //按分类查询
    @Override
    public IPage<Blog> pageByTypeId(int typeId, int page, int size) {
        Page<Blog> pageRequest = new Page<>(page, size);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", typeId)
                .eq("published", true)
                .orderByDesc("update_time");
        return blogMapper.selectPage(pageRequest, queryWrapper);
    }

    @Override
    public Blog getAndConvert(int id) {
        Blog blog = blogMapper.findone(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog(); // 不希望直接操作数据库，希望数据库中还是存着旧的数据
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }


//    根据id获取blog
    @Override
    public Blog findone(int id) {

        return blogMapper.findone(id);
    }
//    分页
    @Override
    public Page<Blog> listBlog(Integer page, Integer size, Blog blog) {
        Page<Blog> pageRequest = new Page<>(page, size);

        // 构建查询条件
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();

        // 添加动态查询条件
        if (StringUtils.hasText(blog.getTitle()) || blog.getTypeId() != null || blog.getRecommened() != null) {
            queryWrapper.and(w -> {
                // 标题模糊匹配
                if (StringUtils.hasText(blog.getTitle())) {
                    w.or().like(Blog::getTitle, blog.getTitle());
                }
                // 分类精确匹配
                if (blog.getTypeId() != null) {
                    w.or().eq(Blog::getTypeId, blog.getTypeId());
                }
                // 推荐状态匹配
                if (blog.getRecommened() != null) {
                    w.or().eq(Blog::getRecommened, blog.getRecommened());
                }
            });
        }

        // 按更新时间倒序排序
        queryWrapper.orderByDesc(Blog::getUpdateTime);

        // 执行查询
        Page<Blog> resultPage = blogMapper.selectPage(pageRequest, queryWrapper);

        return resultPage != null ? resultPage : new Page<>();
    }

//    保存
    @Override
    public Integer saveBlog(Blog blog) {
        if(blog.getId() == null) { //新增
            blog.setViews(0);
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
        } else { //修改
            blog.setUpdateTime(new Date());
        }

        return blogMapper.save(blog);
    }
//    更新
    @Override
    public Integer updateBlog(int id, Blog blog) {
        Blog b = blogMapper.findone(id);
        if (b == null) {
            throw new NotFoundException("不存在该类型");
        }
        blog.setId(id);
        return blogMapper.updateBlog(blog);
    }
//    删除
    @Override
    public void deleteBlog(int id) {
        blogMapper.delete(id);
    }
}
