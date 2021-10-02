package elk.cloud.api.vo;

import elk.cloud.api.bean.SearchBingResult;

import java.util.List;

public class SearchBingResponse {
    /**
     * 耗时
     */
    private long time;

    /**
     * 结果
     */
    private List<SearchBingResult> resuts;

    /**
     * 请求是否成功
     */
    private boolean isSuccess = true;

    /**
     * 错误信息
     */
    private String errorMessage;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<SearchBingResult> getResuts() {
        return resuts;
    }

    public void setResuts(List<SearchBingResult> resuts) {
        this.resuts = resuts;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
