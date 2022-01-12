package com.sbs.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sbs.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {

	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			`nickname` = #{nickname},
			cellphoneNo = #{cellphoneNo},
			email = #{email}
									""")
	void join(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name,
			@Param("nickname") String nickname, @Param("cellphoneNo") String cellphoneNo, @Param("email") String email);

	@Select("SELECT LAST_INSERT_ID()")
	int getLastInsertId();

	@Select("""
			SELECT *
			FROM `member` AS m
			WHERE m.id = #{id}
			""")
	Member getMemberById(@Param("id") int id);

	@Select("""
			SELECT *
			From `member` AS m
			WHERE m.loginId = #{loginId}
			""")
	Member getMemberByLoginId(@Param("loginId") String loginId);

	@Select("""
			SELECT *
			From `member` AS m
			WHERE m.name = #{name}
			AND m.email = #{email}
			""")
	Member getMemberByNameAndEmail(@Param("name") String name, @Param("email") String email);

	@Update("""
			<script>
			UPDATE `member`
			<set>
			<if test="nickname != null">
			nickname = #{nickname},
			</if>
			<if test="loginPw != null">
			loginPw = #{loginPw},
			</if>
			<if test="email != null">
			email = #{email},
			</if>
			<if test="cellphoneNo != null">
			cellphoneNo = #{cellphoneNo},
			</if>
			</set>
			WHERE id = #{id}
			</script>
			""")
	void modify(int id, String nickname, String loginPw, String email, String cellphoneNo);

}
