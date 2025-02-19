package com.oj.judge.codesandbox.impl;

import com.oj.judge.codesandbox.CodeSandbox;
import com.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * Call the code sandbox from third-parties(existing available code sandboxes from third-parties)
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        System.out.println("Third-party Code Sandbox");
        return null;
    }
}
