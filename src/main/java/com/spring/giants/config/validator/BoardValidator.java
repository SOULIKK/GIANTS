package com.spring.giants.config.validator;

import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.entity.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardRequestDto boardRequestDto = (BoardRequestDto) target;
        if (StringUtils.isEmpty(boardRequestDto.getContent())) {
            errors.rejectValue("content", "key", "나용을 입력해주세요.");
        }
    }
}
