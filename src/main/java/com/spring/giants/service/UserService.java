package com.spring.giants.service;


import com.spring.giants.config.MailHandler;
import com.spring.giants.exception.ApiRequestException;
import com.spring.giants.model.dto.*;

import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.model.entity.TempUser;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.entity.UserRole;
import com.spring.giants.model.repository.EditorsPickRepository;
import com.spring.giants.model.repository.TempUserRepository;
import com.spring.giants.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private EditorsPickRepository editorsPickRepository;
    final private TempUserRepository tempUserRepository;

    @Transactional
    public User join(User user) {
        String encodedPw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        user.setEnabled(true);
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }


    @Transactional
    public void updatePw(String username, String password) {

        User user = userRepository.findByUsername(username);
        ProfileRequestDto profileRequestDto = new ProfileRequestDto();

        String encodedPw = passwordEncoder.encode(password);
        profileRequestDto.setPassword(encodedPw);

        user.update(profileRequestDto);
    }


    // my bookmark
    public PageResultDto<MyBookMarksDto, Object[]> getMyBookMark(String username, PageRequestDto pageRequestDto) {
        User user = userRepository.findByUsername(username);
        Function<Object[], MyBookMarksDto> fn = (en -> entityToDto((EditorsPick)en[0] ));

        Page<Object[]> result = editorsPickRepository.myBookmark(
                user,
                pageRequestDto.getType(),
                pageRequestDto.getKeyword(),
                pageRequestDto.getPageable(Sort.by("epId").descending())
        );

        return new PageResultDto<>(result, fn);
    }

    private MyBookMarksDto entityToDto(EditorsPick editorsPick) {
        MyBookMarksDto myBookMarksDto = MyBookMarksDto.builder()
                .epId(editorsPick.getEpId())
                .title(editorsPick.getTitle())
                .description(editorsPick.getDescription())
                .build();
        return myBookMarksDto;
    }

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FROM_ADDRESS;

    public void sendMail(String email) {

        System.out.println("email = " + email);
        int secreteKey = createSecretKey();
        setCertKey(email, secreteKey);

        try {
            MailHandler mailHandler = new MailHandler(mailSender);
            mailHandler.setTo(email);
            mailHandler.setFrom(FROM_ADDRESS);
            mailHandler.setSubject("GIANTS 인증번호입니다.");
            String content = "<h3 style='padding: 1rem; text-align: center;'>회원가입 인증번호입니다.</h3><br>" +
                "<h1 style='padding: 2.5rem; background: #f1f1f1; text-align: center; color: #dc3545;'>"+secreteKey+"</h1>";
            mailHandler.setText(content, true);
            mailHandler.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Transactional
    public TempUser setCertKey(String email, int secreteKey) {
        TempUser tempUser = new TempUser();
        tempUser.setEmail(email);
        tempUser.setCertKey(secreteKey);
        return tempUserRepository.save(tempUser);
    }


    public static int createSecretKey() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }


    public Boolean chkCert(String email, int certKey) {

        TempUser tempUser = tempUserRepository.findByEmail(email);
        if (tempUser.getCertKey() == certKey) {
            return true;
        }
        return false;
    }
}
