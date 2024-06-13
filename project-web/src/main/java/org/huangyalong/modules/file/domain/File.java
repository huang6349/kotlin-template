package org.huangyalong.modules.file.domain;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Opt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import com.mybatisflex.core.handler.JacksonTypeHandler;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.enums.IsDeleted;
import org.huangyalong.modules.file.enums.FileStatus;

import java.util.Date;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_file")
@Schema(name = "文件信息")
public class File extends Model<File> {

    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    @Schema(description = "数据主键")
    private String id;

    @Schema(description = "文件访问地址")
    private String url;

    @Schema(description = "文件大小，单位字节")
    private Long size;

    @Schema(description = "文件名称")
    private String filename;

    @Schema(description = "原始文件名")
    private String origFilename;

    @Schema(description = "基础存储路径")
    private String basePath;

    @Schema(description = "存储路径")
    private String path;

    @Schema(description = "文件扩展名")
    private String ext;

    @Schema(description = "MIME类型")
    private String contentType;

    @Schema(description = "存储平台")
    private String platform;

    @Schema(description = "缩略图访问路径")
    private String thUrl;

    @Schema(description = "缩略图名称")
    private String thFilename;

    @Schema(description = "缩略图大小，单位字节")
    private Long thSize;

    @Schema(description = "缩略图MIME类型")
    private String thContentType;

    @Schema(description = "文件所属对象ID")
    private String objectId;

    @Schema(description = "文件所属对象类型，例如用户头像，评价图片")
    private String objectType;

    @Column(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "文件元数据")
    private Map<String, String> metadata;

    @Column(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "文件用户元数据")
    private Map<String, String> userMetadata;

    @Column(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "缩略图元数据")
    private Map<String, String> thMetadata;

    @Column(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "缩略图用户元数据")
    private Map<String, String> thUserMetadata;

    @Column(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "附加属性")
    private Dict attr;

    @Schema(description = "文件ACL")
    private Object fileAcl;

    @Schema(description = "缩略图文件ACL")
    private Object thFileAcl;

    @Column(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "哈希信息")
    private HashInfo hashInfo;

    @Schema(description = "上传ID，仅在手动分片上传时使用")
    private String uploadId;

    @Schema(description = "上传状态，仅在手动分片上传时使用，1：初始化完成，2：上传完成")
    private Integer uploadStatus;

    @JKDictFormat
    @Schema(description = "文件状态")
    private FileStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date updateTime;

    @Column(version = true)
    @JsonIgnore
    @Schema(description = "更新版本")
    private Long version;

    @Column(isLogicDelete = true)
    @JsonIgnore
    @Schema(description = "是否删除")
    private IsDeleted isDeleted;

    /****************** without ***************/

    @SuppressWarnings("DuplicatedCode")
    public FileInfo without() {
        FileInfo fileInfo = new FileInfo();
        Opt.ofNullable(this)
                .map(File::getId)
                .ifPresent(fileInfo::setId);
        Opt.ofNullable(this)
                .map(File::getUrl)
                .ifPresent(fileInfo::setUrl);
        Opt.ofNullable(this)
                .map(File::getSize)
                .ifPresent(fileInfo::setSize);
        Opt.ofNullable(this)
                .map(File::getFilename)
                .ifPresent(fileInfo::setFilename);
        Opt.ofNullable(this)
                .map(File::getOrigFilename)
                .ifPresent(fileInfo::setOriginalFilename);
        Opt.ofNullable(this)
                .map(File::getBasePath)
                .ifPresent(fileInfo::setBasePath);
        Opt.ofNullable(this)
                .map(File::getPath)
                .ifPresent(fileInfo::setPath);
        Opt.ofNullable(this)
                .map(File::getExt)
                .ifPresent(fileInfo::setExt);
        Opt.ofNullable(this)
                .map(File::getContentType)
                .ifPresent(fileInfo::setContentType);
        Opt.ofNullable(this)
                .map(File::getPlatform)
                .ifPresent(fileInfo::setPlatform);
        Opt.ofNullable(this)
                .map(File::getThUrl)
                .ifPresent(fileInfo::setThUrl);
        Opt.ofNullable(this)
                .map(File::getThFilename)
                .ifPresent(fileInfo::setThFilename);
        Opt.ofNullable(this)
                .map(File::getThSize)
                .ifPresent(fileInfo::setThSize);
        Opt.ofNullable(this)
                .map(File::getThContentType)
                .ifPresent(fileInfo::setThContentType);
        Opt.ofNullable(this)
                .map(File::getObjectId)
                .ifPresent(fileInfo::setObjectId);
        Opt.ofNullable(this)
                .map(File::getObjectType)
                .ifPresent(fileInfo::setObjectType);
        Opt.ofNullable(this)
                .map(File::getMetadata)
                .ifPresent(fileInfo::setMetadata);
        Opt.ofNullable(this)
                .map(File::getUserMetadata)
                .ifPresent(fileInfo::setUserMetadata);
        Opt.ofNullable(this)
                .map(File::getThMetadata)
                .ifPresent(fileInfo::setThMetadata);
        Opt.ofNullable(this)
                .map(File::getThUserMetadata)
                .ifPresent(fileInfo::setThUserMetadata);
        Opt.ofNullable(this)
                .map(File::getAttr)
                .ifPresent(fileInfo::setAttr);
        Opt.ofNullable(this)
                .map(File::getFileAcl)
                .ifPresent(fileInfo::setFileAcl);
        Opt.ofNullable(this)
                .map(File::getThFileAcl)
                .ifPresent(fileInfo::setThFileAcl);
        Opt.ofNullable(this)
                .map(File::getHashInfo)
                .ifPresent(fileInfo::setHashInfo);
        Opt.ofNullable(this)
                .map(File::getUploadId)
                .ifPresent(fileInfo::setUploadId);
        Opt.ofNullable(this)
                .map(File::getUploadStatus)
                .ifPresent(fileInfo::setUploadStatus);
        Opt.ofNullable(this)
                .map(File::getCreateTime)
                .ifPresent(fileInfo::setCreateTime);
        return fileInfo;
    }

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public File with(FileInfo fileInfo) {
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getId)
                .ifPresent(this::setId);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getUrl)
                .ifPresent(this::setUrl);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getSize)
                .ifPresent(this::setSize);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getFilename)
                .ifPresent(this::setFilename);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getOriginalFilename)
                .ifPresent(this::setOrigFilename);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getBasePath)
                .ifPresent(this::setBasePath);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getPath)
                .ifPresent(this::setPath);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getExt)
                .ifPresent(this::setExt);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getContentType)
                .ifPresent(this::setContentType);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getPlatform)
                .ifPresent(this::setPlatform);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getThUrl)
                .ifPresent(this::setThUrl);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getThFilename)
                .ifPresent(this::setThFilename);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getThSize)
                .ifPresent(this::setThSize);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getThContentType)
                .ifPresent(this::setThContentType);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getObjectId)
                .ifPresent(this::setObjectId);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getObjectType)
                .ifPresent(this::setObjectType);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getMetadata)
                .ifPresent(this::setMetadata);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getUserMetadata)
                .ifPresent(this::setUserMetadata);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getThMetadata)
                .ifPresent(this::setThMetadata);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getThUserMetadata)
                .ifPresent(this::setThUserMetadata);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getAttr)
                .ifPresent(this::setAttr);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getFileAcl)
                .ifPresent(this::setFileAcl);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getThFileAcl)
                .ifPresent(this::setThFileAcl);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getHashInfo)
                .ifPresent(this::setHashInfo);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getUploadId)
                .ifPresent(this::setUploadId);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getUploadStatus)
                .ifPresent(this::setUploadStatus);
        Opt.ofNullable(fileInfo)
                .map(FileInfo::getCreateTime)
                .ifPresent(this::setCreateTime);
        return this;
    }
}
