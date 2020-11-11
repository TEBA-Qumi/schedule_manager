package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Team;
import utils.DBUtil;

public class TeamValidator {
    public static List<String> validate(Team t, Boolean password_check_flag, Boolean team_code_duplicate_check_flag) {
        List<String> errors = new ArrayList<String>();

        String team_code_error = _validateTeam_code(t.getTeam_code(), team_code_duplicate_check_flag);
        if(!team_code_error.equals("")) {
            errors.add(team_code_error);
        }

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

    private static String _validateTeam_code(String team_code, Boolean team_code_duplicate_check_flag) {
        // 必須入力チェック
        if(team_code == null || team_code.equals("")) {
            return "チーム番号を入力してください。";
        }

        // すでに登録されている社員番号との重複チェック
        if(team_code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long teams_count = (long)em.createNamedQuery("checkRegisteredTeam_code", Long.class)
                                           .setParameter("team_code", team_code)
                                             .getSingleResult();
            em.close();
            if(teams_count > 0) {
                return "入力されたチーム番号はすでに存在しています。";
            }
        }

        return "";
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
