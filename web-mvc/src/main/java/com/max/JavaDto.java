package com.max;

import java.util.List;

/**
 * Created by mbondarenko on 12.08.2014.
 */
public class JavaDto {

    public String name;
    public Long id;
    public List<JavaDto> ch;

    public JavaDto() {
    }

    public JavaDto(String name, Long id, List<JavaDto> ch) {
        this.name = name;
        this.id = id;
        this.ch = ch;
    }
}
