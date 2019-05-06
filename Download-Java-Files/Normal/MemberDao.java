package edu.mum.library.dataaccess;

import org.springframework.stereotype.Component;

import edu.mum.library.dataaccess.base.BaseDaoWithPrimaryKey;
import edu.mum.library.model.Member;

@Component
public class MemberDao extends BaseDaoWithPrimaryKey<Member, String> {

	@Override
	public String getTableName() {
		return "member";
	}

}
