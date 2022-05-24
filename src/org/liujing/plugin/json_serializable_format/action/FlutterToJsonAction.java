// 
// Decompiled by Procyon v0.5.36
// 

package org.liujing.plugin.json_serializable_format.action;

import com.intellij.openapi.command.WriteCommandAction;
import java.io.IOException;
import com.intellij.openapi.ui.Messages;
import org.liujing.plugin.json_serializable_format.utils.ClassProcessor;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.liujing.plugin.json_serializable_format.model.ClassModel;
import java.util.List;
import org.liujing.plugin.json_serializable_format.utils.JsonTransformerUtils;
import org.liujing.plugin.json_serializable_format.utils.StringUtils;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.liujing.plugin.json_serializable_format.view.writablepannel.WritablePannel;
import org.liujing.plugin.json_serializable_format.view.writablepannel.OnClickListener;
import com.intellij.openapi.actionSystem.AnAction;

public class FlutterToJsonAction extends AnAction implements OnClickListener
{
    private WritablePannel writablePannel;
    private VirtualFile virtualFile;
    private Project project;
    
    public void actionPerformed(final AnActionEvent anActionEvent) {
        if (anActionEvent == null) {
            return;
        }
        final Editor editor = (Editor)anActionEvent.getData(PlatformDataKeys.EDITOR);
        this.project = (Project)anActionEvent.getData(PlatformDataKeys.PROJECT);
        if (editor == null || this.project == null) {
            return;
        }
        final PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, this.project);
        if (psiFile == null) {
            return;
        }
        this.writablePannel = new WritablePannel().builder(this);
        this.virtualFile = psiFile.getVirtualFile();
        this.writablePannel.setVisible(true);
    }
    
    public void onViewClick(final String json) {
        if (json != null) {
            this.writablePannel.dispose();
            final String nameWithoutExtension = this.virtualFile.getNameWithoutExtension();
            final String className = StringUtils.toUpperCamel(nameWithoutExtension);
            final List<ClassModel> classModels = JsonTransformerUtils.transformerJson(json, className);
            final List<ClassModel> newClassModels = classModels.stream().distinct().collect((Collector<? super Object, ?, List<ClassModel>>)Collectors.toList());
            final String beanStr = ClassProcessor.buildClass(newClassModels, nameWithoutExtension);
            this.writeClass(beanStr);
        }
        else {
            this.showInfoDialog("json error!");
        }
    }
    
    private void showInfoDialog(final String message) {
        Messages.showErrorDialog(message, "Info");
    }
    
    private void writeClass(final String classStr) {
        WriteCommandAction.runWriteCommandAction(this.project, () -> {
            try {
                this.virtualFile.setBinaryContent(classStr.getBytes());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
