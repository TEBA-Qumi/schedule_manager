package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Account;
import utils.DBUtil;

public class AccountValidator {
    public static List<String> validate(Account a, Boolean code_duplicate_check_flag, Boolean password_check_flag){
        List<String> errors = new ArrayList<String>();

        // アカウント番号に関する不備があった場合、エラーの中身がcode_errorに入る
        String code_error = _validateCode(a.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")){
            errors.add(code_error);
        }

        String name_error = _validateName(a.getName());
        if(!name_error.equals("")){
            errors.add(name_error);
        }

        String password_error = _validatePassword(a.getPassword(), password_check_flag);
        if(!password_error.equals("")){
            errors.add(password_error);
        }

        return errors;
    }
    //アカウント番号が正しく入力されているかのチェック
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        //必須入力チェック
        if(code == null || code.equals("")) {
            return "アカウント番号を入力してください。";
        }

        //すでに登録された番号との重複チェック
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long account_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                                    .setParameter("code", code)
                                                    .getSingleResult();
            em.close();
            if(account_count > 0) {
                return "入力されたアカウント番号はすでに存在しています。";
            }
        }
        return "";
    }

    //アカウント名が正しく入力されているかチェック
    public static String _validateName(String name){
        if(name == null || name.equals("")) {
            return "名前を入力してください。";
        }

        return "";
    }

    //パスワードが正しく入力されたかチェック
    public static String _validatePassword(String password, Boolean password_check_flag) {
        //パスワード変更する場合のみ実行する
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}
