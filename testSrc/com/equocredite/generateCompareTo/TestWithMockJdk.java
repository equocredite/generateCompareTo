package com.equocredite.generateCompareTo;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class TestWithMockJdk extends LightJavaCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return new File(getSourceRoot(), "testData").getPath();
    }

    private static File getSourceRoot() {
        String testOutput = PathManager.getJarPathForClass(TestDefault.class);
        return new File(testOutput, "../../..");
    }

    private static class MyDescriptor extends DefaultLightProjectDescriptor {
        @Override
        public Sdk getSdk() {
            return JavaSdk.getInstance().createJdk("1.7", new File(getSourceRoot(), "mockJDK-1.7").getPath(),
                    false);
        }
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new MyDescriptor();
    }
}
