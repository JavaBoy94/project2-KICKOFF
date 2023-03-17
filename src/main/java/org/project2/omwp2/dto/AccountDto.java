package org.project2.omwp2.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountDto {

    private Long acId;
    private String acTitle;
    private String acContent;
    private int acIncome;
    private int acExpend;
    private int acSurplus;



}
