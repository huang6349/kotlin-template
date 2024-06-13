package org.huangyalong.modules.file.domain;

import cn.hutool.core.convert.Convert;
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
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.huangyalong.core.enums.IsDeleted;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_file_part")
@Schema(name = "文件分片")
public class FilePart extends Model<FilePart> {

    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "数据主键")
    private Long id;

    @Schema(description = "存储平台")
    private String platform;

    @Schema(description = "分片ETag")
    private String eTag;

    @Schema(description = "分片号。每一个上传的分片都有一个分片号，一般情况下取值范围是1~10000")
    private Integer partNumber;

    @Schema(description = "文件大小，单位字节")
    private Long partSize;

    @Column(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "哈希信息")
    private HashInfo hashInfo;

    @Schema(description = "上传ID，仅在手动分片上传时使用")
    private String uploadId;

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

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public FilePart with(FilePartInfo filePartInfo) {
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getId)
                .map(Convert::toLong)
                .ifPresent(this::setId);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getPlatform)
                .ifPresent(this::setPlatform);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getETag)
                .ifPresent(this::setETag);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getPartNumber)
                .ifPresent(this::setPartNumber);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getPartSize)
                .ifPresent(this::setPartSize);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getHashInfo)
                .ifPresent(this::setHashInfo);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getUploadId)
                .ifPresent(this::setUploadId);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getCreateTime)
                .ifPresent(this::setCreateTime);
        Opt.ofNullable(filePartInfo)
                .map(FilePartInfo::getLastModified)
                .ifPresent(this::setUpdateTime);
        return this;
    }
}
