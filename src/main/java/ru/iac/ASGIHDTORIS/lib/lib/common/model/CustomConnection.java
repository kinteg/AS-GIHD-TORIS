package ru.iac.ASGIHDTORIS.lib.lib.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomConnection {

    private String URL;
    private String username;
    private String password;

}
