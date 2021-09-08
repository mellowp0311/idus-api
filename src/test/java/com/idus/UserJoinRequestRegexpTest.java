package com.idus;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UserJoinRequestRegexpTest {

    @Test
    public void 회원_이름_정규식_체크(){
        assertThat(checkName("루비아스")).isTrue();
        assertThat(checkName("한글Aa")).isTrue();
        assertThat(checkName("AAaa#")).isFalse();
        assertThat(checkName("a")).isTrue();
    }

    /**
     * 한글, 영문 대/소문자만 허용
     * @param str
     * @return
     */
    private boolean checkName(String str){
        return Pattern.matches("^[a-zA-Z가-힣]*$", str);
    }

    @Test
    public void 회원_별명_정규식_체크(){
        assertThat(checkNickName("aAaaBB")).isFalse();
        assertThat(checkNickName("###")).isFalse();
        assertThat(checkNickName("가aA")).isFalse();
        assertThat(checkNickName("aa")).isTrue();
        assertThat(checkNickName("banana`")).isFalse();
        assertThat(checkNickName("apple")).isTrue();
    }

    /**
     * 영문 소문자만 허용
     *
     * @param str
     * @return
     */
    private boolean checkNickName(String str){
        return Pattern.matches("^[a-z]*$", str);
    }

    @Test
    public void 회원_비밀번호_정규식_체크(){
        assertThat(checkPassword("Aa#3정상케이스입니다")).isTrue();
        assertThat(checkPassword("Aa#3정상케이스입니다 띄어쓰기")).isTrue();
        assertThat(checkPassword("a#3대문자가없어요")).isFalse();
        assertThat(checkPassword("A#3소문자가없어요")).isFalse();
        assertThat(checkPassword("Aa#숫자가없어요")).isFalse();
        assertThat(checkPassword("Aa특수문자가없어요")).isTrue();
    }

    /**
     * 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상씩 포함
     *
     * @param str
     * @return
     */
    private boolean checkPassword(String str){
        return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{4,30}$", str);
    }


    @Test
    public void 회원_전화번호_정규식_체크(){
        assertThat(checkPhoneNo("01066040344")).isTrue();
        assertThat(checkPhoneNo("023334444")).isTrue();
        assertThat(checkPhoneNo("0233334444")).isTrue();
        assertThat(checkPhoneNo("010-6604-0344")).isFalse();
        assertThat(checkPhoneNo("02-3333-4444")).isFalse();
        assertThat(checkPhoneNo("02-333-4444")).isFalse();
    }

    /**
     * 숫자만
     *
     * @param str
     * @return
     */
    private boolean checkPhoneNo(String str){
        return Pattern.matches("^[0-9]*$", str);
    }


    @Test
    public void 회원_이메일_정규식_체크(){
        // @Email 어노테이션 이용
    }
}
