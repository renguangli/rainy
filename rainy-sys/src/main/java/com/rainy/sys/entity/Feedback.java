package com.rainy.sys.entity;

import lombok.Data;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/3 4:46 PM
 */
@Data
public class Feedback {

    private String name;
    private String email;
    private String feedbackType;
    private String message;
    private Long timestamp;

}
