package com.ritian.modules.sys.dto;

import lombok.Data;

/**
 * TODO
 * @author ritian.Zhang
 * @date 2019/01/08
 **/
@Data
public abstract class pageDTO {

    private int currPage = 1;

    private int limit = 10;
}
