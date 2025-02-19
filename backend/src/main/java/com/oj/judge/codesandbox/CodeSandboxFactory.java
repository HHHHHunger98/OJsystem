package com.oj.judge.codesandbox;

import com.oj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.oj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.oj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * Code sandbox factory: instantiate a specific code sandbox object based on user input
 */
public class CodeSandboxFactory {

    /**
     * Instantiate a CodeSandbox object.
     * @param type sandbox type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
