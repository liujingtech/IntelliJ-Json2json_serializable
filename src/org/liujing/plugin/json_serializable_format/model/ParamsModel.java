// 
// Decompiled by Procyon v0.5.36
// 

package org.liujing.plugin.json_serializable_format.model;

import java.util.Objects;

public class ParamsModel
{
    private int type;
    private String originName;
    private String camelName;
    private String typeName;
    
    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
    
    public int getType() {
        return this.type;
    }
    
    public void setType(final int type) {
        this.type = type;
    }
    
    public String getOriginName() {
        return this.originName;
    }
    
    public void setOriginName(final String originName) {
        this.originName = originName;
    }
    
    public String getLowerCamelName() {
        return this.camelName;
    }
    
    public void setCamelName(final String camelName) {
        this.camelName = camelName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParamsModel)) {
            return false;
        }
        final ParamsModel that = (ParamsModel)o;
        return this.getType() == that.getType() && Objects.equals(this.getOriginName(), that.getOriginName()) && Objects.equals(this.getLowerCamelName(), that.getLowerCamelName()) && Objects.equals(this.getTypeName(), that.getTypeName());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getType(), this.getOriginName(), this.getLowerCamelName(), this.getTypeName());
    }
}
