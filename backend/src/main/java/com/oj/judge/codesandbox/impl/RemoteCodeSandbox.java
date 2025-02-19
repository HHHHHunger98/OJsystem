package com.oj.judge.codesandbox.impl;

import com.oj.judge.codesandbox.CodeSandbox;
import com.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * Call the remote code sandbox
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("Remote Code Sandbox");
        return null;
    }
}
