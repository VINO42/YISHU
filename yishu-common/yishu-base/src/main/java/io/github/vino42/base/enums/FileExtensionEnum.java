package io.github.vino42.base.enums;

public enum FileExtensionEnum {
    PDF(".pdf"),
    JPG(".jpg"),
    PNG(".png"),
    BMP(".bmp"),
    GIF(".gif"),
    JPEG(".jpeg"),
    HTML(".html"),
    TXT(".txt"),
    VSD(".vsd"),
    PPTX(".pptx"),
    DOCX(".docx"),
    XML(".xml"),
    PPT(".ppt"),
    EPUB(".epub"),
    MOBI(".mobi"),
    DOC(".doc");

    /**
     * 后缀名
     */
    public String ext;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    FileExtensionEnum(String ext) {
        this.ext = ext;
    }
}
