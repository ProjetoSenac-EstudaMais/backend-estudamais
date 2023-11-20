package br.com.api.estudamais.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequest {
    private Long postId;
    private Long userId;
}

