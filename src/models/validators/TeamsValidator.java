package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Team;
import utils.DBUtil;

public class TeamsValidator {
    public static List<String> validate(Team t, Boolean team_code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String Team_code_error = _validateTeam_code(t.getTeam_code(), team_code_duplicate_check_flag);
        if(!Team_code_error.equals("")) {
            errors.add(Team_code_error);
        }

        String name_error = _validateName(t.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(t.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    // チーム番号
    private static String _validateTeam_code(String team_code, Boolean team_code_duplicate_check_flag) {
        // 必須入力チェック
        if(team_code == null || team_code.equals("")) {
            return "チーム番号を入力してください。";
        }

        // すでに登録されているチーム番号との重複チェック
        if(team_code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long teams_count = (long)em.createNamedQuery("checkRegisteredTeam_code", Long.class)
                                           .setParameter("team_code", team_code)
                                             .getSingleResult();
            em.close();
            if(teams_count > 0) {
                return "入力されたチーム番号の情報はすでに存在しています。";
            }
        }

        return "";
    }

    // チーム名の必須入力チェック
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "チーム名を入力してください。";
        }

        return "";
    }

    // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}
