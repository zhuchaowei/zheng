package com.zheng.upms.client.shiro.subjectfactory;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * Created by zhu on 2017/6/27.
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context)
    {
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
