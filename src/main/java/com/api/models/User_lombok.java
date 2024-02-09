package com.api.models;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class User_lombok {
    private String id;
    private String name;
    private String email;
    private String gender;
    private String status;
}
