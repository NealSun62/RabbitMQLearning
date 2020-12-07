package com.sun.overweight.enums;

public enum FileType {
    TEX(1, ".txt"),
    XLS(2, ".xls"),
    XLSX(3, ".xlsx"),
    DOC(4, ".doc"),
    DOCX(5, ".docx"),
    PDF(6, ".pdf"),
    HTML(7, ".html"),
    ZIP(8, ".zip"),
    RAR(9, ".rar"),
    TMP(11, ".tmp"),
    EML(12, ".eml"),
    JPG(13, ".jpg"),
    PNG(14, ".png"),
    TEMPLT(15, ".dotx"),
    PPT(16, ".ppt"),
    PPTX(17, ".pptx"),
    JPEG(18, ".jpeg"),
    XLSM(19, ".xlsm"),
    CSV(20, ".csv"),
    GIF(21, ".gif"),
    MD(22, ".md"),
    SVG(23, ".svg"),
    BMP(24, ".bmp"),
    MSG(25, ".msg"),
    XML(26, ".xml"),
    LOG(27, ".log"),
    RTF(28, ".rtf"),
    DOCM(29, ".docm"),
    DEFAULT(99, ".fais-attach");

    private Integer fileType;
    private String fileSuffix;
    private static final String POINT = ".";
    public static final int FILE_SIZE_MAX = 10485760;
    public static final String[] OFFICE_SUFFIX = new String[]{"doc", "docx", "rtf", "docm", "xls", "xlsx", "xlsm", "ppt", "pptx"};
    public static final String[] OFFICE_SUFFIX_POINT = new String[]{".doc", ".docx", ".rtf", ".docm", ".xls", ".xlsx", ".xlsm", ".ppt", ".pptx"};

    private FileType(Integer fileType, String fileSuffix) {
        this.fileType = fileType;
        this.fileSuffix = fileSuffix;
    }

    public Integer getFileType() {
        return this.fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileSuffix() {
        return this.fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public static FileType parseFileType(String fileName) {
        try {
            if (fileName != null && fileName.lastIndexOf(".") >= 0) {
                String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                FileType[] var2 = values();
                int var3 = var2.length;

                for (int var4 = 0; var4 < var3; ++var4) {
                    FileType ft = var2[var4];
                    if (fileType.equals(ft.getFileSuffix())) {
                        return ft;
                    }
                }
                return DEFAULT;
            }else{
                throw new Exception("附件名称必须为全称，包括前缀和后缀");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getExtNameWithoutPoint(String name) {
        return name.substring(1);
    }
}
