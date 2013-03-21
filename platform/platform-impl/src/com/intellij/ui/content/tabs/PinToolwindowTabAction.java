/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.ui.content.tabs;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.impl.content.ToolWindowContentUi;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.TabbedPaneContentUI;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author spleaner
 */
public class PinToolwindowTabAction extends ToggleAction implements DumbAware {
  @NonNls public static final String ACTION_NAME = "PinToolwindowTab";

  /**
   * Looks up the pin action from the ActionManager and registers the default shortcut set
   * @param component the component where the default shortcutSet should be registered
   */
  public static AnAction getPinAction(JComponent component) {
    AnAction action = ActionManager.getInstance().getAction(ACTION_NAME);
    CustomShortcutSet customShortcutSet =
      new CustomShortcutSet(KeymapManager.getInstance().getActiveKeymap().getShortcuts(IdeActions.ACTION_PIN_ACTIVE_TAB));
    action.registerCustomShortcutSet(customShortcutSet, component);
    return action;
  }

  /**
   * Constructor called from ActionManager, be sure to call registerCustomShortcutSet or use the other constructor
   */
  public PinToolwindowTabAction() {
    super("Pin Tab", "Pin tool window tab", AllIcons.General.Pin_tab);
  }

  /**
   * @param component the component where the default shortcut set should be registered
   */
  public PinToolwindowTabAction(JComponent component) {
    this();
    CustomShortcutSet customShortcutSet =
      new CustomShortcutSet(KeymapManager.getInstance().getActiveKeymap().getShortcuts(IdeActions.ACTION_PIN_ACTIVE_TAB));
    registerCustomShortcutSet(customShortcutSet, component);
  }


  @Nullable
  private static Content getContextContent(@NotNull AnActionEvent event) {
    final ToolWindow window = PlatformDataKeys.TOOL_WINDOW.getData(event.getDataContext());
    if (window != null) {
      final ContentManager contentManager = window.getContentManager();
      if (contentManager != null) {
        return contentManager.getSelectedContent();
      }
    }

    return null;
  }

  @Override
  public boolean isSelected(AnActionEvent event) {
    final Content content = getContextContent(event);
    return content != null && content.isPinned();
  }

  @Override
  public void setSelected(AnActionEvent event, boolean flag) {
    final Content content = getContextContent(event);
    if (content != null) content.setPinned(flag);
  }

  @Override
  public void update(AnActionEvent event) {
    super.update(event);
    Presentation presentation = event.getPresentation();
    final Content content = getContextContent(event);
    boolean enabled = content != null && content.isPinnable();

    if (enabled) {
      presentation.setIcon(
        TabbedPaneContentUI.POPUP_PLACE.equals(event.getPlace()) || ToolWindowContentUi.POPUP_PLACE.equals(event.getPlace()) ? null : AllIcons.General.Pin_tab);
    }

    presentation.setEnabled(enabled);
    presentation.setVisible(enabled);
  }
}
