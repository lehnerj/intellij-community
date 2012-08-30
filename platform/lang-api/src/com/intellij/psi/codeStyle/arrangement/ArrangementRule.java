/*
 * Copyright 2000-2012 JetBrains s.r.o.
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
package com.intellij.psi.codeStyle.arrangement;

import com.intellij.psi.codeStyle.arrangement.match.ArrangementEntryMatcher;
import com.intellij.psi.codeStyle.arrangement.order.ArrangementEntryOrderType;
import org.jetbrains.annotations.NotNull;

/**
 * Container for the strategies to be used during file entries arrangement. 
 * <p/>
 * Example: we can define a rule like 'private final non-static fields' or 'public static methods' etc.
 * <p/>
 * Not thread-safe.
 * 
 * @author Denis Zhdanov
 * @since 7/17/12 11:07 AM
 */
public class ArrangementRule {

  @NotNull public static final ArrangementEntryOrderType DEFAULT_ORDER_TYPE = ArrangementEntryOrderType.KEEP;

  @NotNull private final ArrangementEntryMatcher   myMatcher;
  @NotNull private final ArrangementEntryOrderType myOrderType;

  public ArrangementRule(@NotNull ArrangementEntryMatcher matcher) {
    this(matcher, DEFAULT_ORDER_TYPE);
  }

  public ArrangementRule(@NotNull ArrangementEntryMatcher matcher, @NotNull ArrangementEntryOrderType type) {
    myMatcher = matcher;
    myOrderType = type;
  }

  @NotNull
  public ArrangementEntryMatcher getMatcher() {
    return myMatcher;
  }

  @NotNull
  public ArrangementEntryOrderType getOrderType() {
    return myOrderType;
  }

  @Override
  public int hashCode() {
    int result = myMatcher.hashCode();
    result = 31 * result + myOrderType.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ArrangementRule that = (ArrangementRule)o;
    return myOrderType == that.myOrderType && myMatcher.equals(that.myMatcher);
  }

  @Override
  public String toString() {
    return String.format("matcher: %s, sort type: %s", myMatcher, myOrderType);
  }
}
