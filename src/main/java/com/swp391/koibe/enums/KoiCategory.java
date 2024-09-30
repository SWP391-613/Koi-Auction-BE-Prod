package com.swp391.koibe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KoiCategory {

    KOHAKU("Kohaku"),
    TAISHO_SANKE("Taisho Sanke"),
    SHOWA_SANSHOKU("Showa Sanshoku"),
    UTSURIMONO("Utsurimono"),
    SHIRO_UTSURI("Shiro Utsuri"),
    HI_UTSURI("Hi Utsuri"),
    KI_UTSURI("Ki Utsuri"),
    ASAGI("Asagi"),
    SHUSUI("Shusui"),
    BEKKO("Bekko"),
    TANCHO("Tancho"),
    GINRIN_KOHAKU("Ginrin Kohaku"),
    GINRIN_SANKE("Ginrin Sanke"),
    GINRIN_SHOWA("Ginrin Showa"),
    DOITSU_KOHAKU("Doitsu Kohaku"),
    DOITSU_SANKE("Doitsu Sanke"),
    DOITSU_SHOWA("Doitsu Showa"),
    KIN_KIKOKURYU("Kin Kikokuryu"),
    KUMONRYU("Kumonryu"),
    OCHIBA_SHIGURE("Ochiba Shigure");

    private final String displayName;

}
