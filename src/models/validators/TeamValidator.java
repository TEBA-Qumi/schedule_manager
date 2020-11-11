package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Team;

public class TeamValidator {
    public static List<String> validate(Team t, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String name_error = _validateName(t.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(t.getPassword(), password_check_flag);
        if(!password_error.equals("")){
            errors.add(password_error);
        }

        return errors;
    }

    private static String _validateName(String name) {
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
