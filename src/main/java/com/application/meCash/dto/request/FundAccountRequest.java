package com.application.meCash.dto.request;


import lombok.*;
import org.hibernate.context.spi.CurrentSessionContext;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundAccountRequest {

    BigDecimal amount;
}
