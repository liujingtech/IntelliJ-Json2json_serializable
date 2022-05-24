// 
// Decompiled by Procyon v0.5.36
// 

package org.liujing.plugin.json_serializable_format.model;

import java.util.Objects;
import java.util.List;

public class ClassModel
{
    private String className;
    private List<ParamsModel> paramsModels;
    
    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(final String className) {
        this.className = className;
    }
    
    public List<ParamsModel> getParamsModels() {
        return this.paramsModels;
    }
    
    public void setParamsModels(final List<ParamsModel> paramsModels) {
        this.paramsModels = paramsModels;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassModel)) {
            return false;
        }
        final ClassModel that = (ClassModel)o;
        return Objects.equals(this.getClassName(), that.getClassName()) && Objects.equals(this.getParamsModels(), that.getParamsModels());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getClassName(), this.getParamsModels());
    }
}
