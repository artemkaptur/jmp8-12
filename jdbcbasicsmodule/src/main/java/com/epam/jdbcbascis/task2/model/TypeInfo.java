package com.epam.jdbcbascis.task2.model;

public class TypeInfo {

    private String typeName;
    private int jdbcTypeCode;
    private int precision;
    private String literalPrefix;
    private String literalSuffix;
    private boolean caseSensitive;
    private boolean unsigned;
    private boolean fixedPrecisionScale;
    private boolean autoIncrement;
    private short minimumScale;
    private short maximumScale;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getJdbcTypeCode() {
        return jdbcTypeCode;
    }

    public void setJdbcTypeCode(int jdbcTypeCode) {
        this.jdbcTypeCode = jdbcTypeCode;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getLiteralPrefix() {
        return literalPrefix;
    }

    public void setLiteralPrefix(String literalPrefix) {
        this.literalPrefix = literalPrefix;
    }

    public String getLiteralSuffix() {
        return literalSuffix;
    }

    public void setLiteralSuffix(String literalSuffix) {
        this.literalSuffix = literalSuffix;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean isUnsigned() {
        return unsigned;
    }

    public void setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
    }

    public boolean isFixedPrecisionScale() {
        return fixedPrecisionScale;
    }

    public void setFixedPrecisionScale(boolean fixedPrecisionScale) {
        this.fixedPrecisionScale = fixedPrecisionScale;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public short getMinimumScale() {
        return minimumScale;
    }

    public void setMinimumScale(short minimumScale) {
        this.minimumScale = minimumScale;
    }

    public short getMaximumScale() {
        return maximumScale;
    }

    public void setMaximumScale(short maximumScale) {
        this.maximumScale = maximumScale;
    }

}
