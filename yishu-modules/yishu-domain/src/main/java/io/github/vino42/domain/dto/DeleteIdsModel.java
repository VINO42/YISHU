package io.github.vino42.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/26 20:38
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class DeleteIdsModel implements Serializable {

    private List<Long> id;

    public List<Long> getId() {
        return id;
    }

    public void setId(List<Long> id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteIdsModel{" +
                "id=" + id +
                '}';
    }
}
