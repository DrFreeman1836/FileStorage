package main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RsDto {

  private Boolean result;

  private Object data;

}
