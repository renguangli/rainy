package com.rainy.sys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/3 4:46 PM
 */
@Data
public class Feedback implements Serializable {

    private static final long serialVersionUID = 6112147421176282592L;

    private String name;
    private String email;
    private String feedbackType;
    private String message;
    private Long timestamp;

}
