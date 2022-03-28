package domain.super_express_type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SuperExpressType {
    HIKARI(new SuperExpressOption(false)),
    NOZOMI(new SuperExpressOption(true));

    @Getter
    private final SuperExpressOption superExpressOption;

}
