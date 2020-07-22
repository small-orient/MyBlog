package blog.entity;

import java.io.Serializable;

public class ResultInfo implements Serializable {
    //false：结果异常，true：结果正常
    private Boolean flag;
    //返回结果数据对象
    private Object data;
    //异常返回信息
    private String errorInfo;

    public ResultInfo() {
    }

    public ResultInfo(boolean flag) {
        this.flag = flag;
    }

    public ResultInfo(Boolean flag,  String errorInfo) {
        this.flag = flag;
        this.errorInfo = errorInfo;
    }

    public ResultInfo(boolean flag, Object data, String errorInfo) {
        this.flag = flag;
        this.data = data;
        this.errorInfo= errorInfo;
    }

    public boolean isFlag() {
        return flag;
    }


    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
