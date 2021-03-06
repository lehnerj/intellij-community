/*
 * Copyright 2000-2014 JetBrains s.r.o.
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

package com.intellij.vcs.log.newgraph.gpaph.impl;

import com.intellij.vcs.log.newgraph.gpaph.GraphElement;
import com.intellij.vcs.log.newgraph.gpaph.actions.InternalGraphAction;
import com.intellij.vcs.log.newgraph.gpaph.actions.MouseOverGraphElementInternalGraphAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThickHoverControllerTest extends AbstractThickHoverController {

  @Nullable
  private GraphElement hoverElement = null;


  @Override
  public boolean isThick(@NotNull GraphElement element) {
    return false;
  }

  @Override
  public boolean isHover(@NotNull GraphElement element) {
    return element.equals(hoverElement);
  }

  @Override
  public void performAction(@NotNull InternalGraphAction action) {
    super.performAction(action);
    if (action instanceof MouseOverGraphElementInternalGraphAction) {
      hoverElement = ((MouseOverGraphElementInternalGraphAction)action).getInfo();
    }
  }
}
