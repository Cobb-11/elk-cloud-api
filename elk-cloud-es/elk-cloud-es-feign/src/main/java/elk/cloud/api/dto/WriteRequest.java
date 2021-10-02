package elk.cloud.api.dto;

import java.util.List;

public class WriteRequest {

    private String _index;

    private String _id;

    private List<String> _source;

    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> get_source() {
        return _source;
    }

    public void set_source(List<String> _source) {
        this._source = _source;
    }
}
