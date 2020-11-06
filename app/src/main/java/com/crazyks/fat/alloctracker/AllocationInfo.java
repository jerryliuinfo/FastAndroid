package com.crazyks.fat.alloctracker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * author: jerry
 * created on: 2020/8/4 11:31 PM
 * description:
 */
public class AllocationInfo implements IStackTraceInfo {
    private final String mAllocatedClass;
    private final int mAllocNumber;
    private final int mAllocationSize;
    private final short mThreadId;
    private final StackTraceElement[] mStackTrace;

    AllocationInfo(int allocNumber, String allocatedClass, int allocationSize, short threadId, StackTraceElement[] stackTrace) {
        this.mAllocNumber = allocNumber;
        this.mAllocatedClass = allocatedClass;
        this.mAllocationSize = allocationSize;
        this.mThreadId = threadId;
        this.mStackTrace = stackTrace;
    }

    public int getAllocNumber() {
        return this.mAllocNumber;
    }

    public String getAllocatedClass() {
        return this.mAllocatedClass;
    }

    public int getSize() {
        return this.mAllocationSize;
    }

    public short getThreadId() {
        return this.mThreadId;
    }

    public StackTraceElement[] getStackTrace() {
        return this.mStackTrace;
    }

    public int compareTo(AllocationInfo otherAlloc) {
        return otherAlloc.mAllocationSize - this.mAllocationSize;
    }

    @Nullable
    public String getAllocationSite() {
        return this.mStackTrace.length > 0 ? this.mStackTrace[0].toString() : null;
    }

    public String getFirstTraceClassName() {
        return this.mStackTrace.length > 0 ? this.mStackTrace[0].getClassName() : null;
    }

    public String getFirstTraceMethodName() {
        return this.mStackTrace.length > 0 ? this.mStackTrace[0].getMethodName() : null;
    }

    public boolean filter(String filter, boolean fullTrace, Locale locale) {
        return this.allocatedClassMatches(filter, locale) || !this.getMatchingStackFrames(filter, fullTrace, locale).isEmpty();
    }

    public boolean allocatedClassMatches(@NonNull String pattern, @NonNull Locale locale) {
        return this.mAllocatedClass.toLowerCase(locale).contains(pattern.toLowerCase(locale));
    }

    @NonNull
    public List<String> getMatchingStackFrames(@NonNull String filter, boolean fullTrace, @NonNull Locale locale) {
        filter = filter.toLowerCase(locale);
        if (this.mStackTrace.length > 0) {
            int length = fullTrace ? this.mStackTrace.length : 1;
//            List<String> matchingFrames = Lists.newArrayListWithExpectedSize(length);
//
//            for(int i = 0; i < length; ++i) {
//                String frameString = this.mStackTrace[i].toString();
//                if (frameString.toLowerCase(locale).contains(filter)) {
//                    matchingFrames.add(frameString);
//                }
//            }
//
//            return matchingFrames;
            return null;
        } else {
            return Collections.emptyList();
        }
    }

    public static final class AllocationSorter implements Comparator<AllocationInfo> {
        private AllocationInfo.SortMode mSortMode;
        private boolean mDescending;

        public AllocationSorter() {
            this.mSortMode = AllocationInfo.SortMode.SIZE;
            this.mDescending = true;
        }

        public void setSortMode(@NonNull AllocationInfo.SortMode mode) {
            if (this.mSortMode == mode) {
                this.mDescending = !this.mDescending;
            } else {
                this.mSortMode = mode;
            }

        }

        public void setSortMode(@NonNull AllocationInfo.SortMode mode, boolean descending) {
            this.mSortMode = mode;
            this.mDescending = descending;
        }

        @NonNull
        public AllocationInfo.SortMode getSortMode() {
            return this.mSortMode;
        }

        public boolean isDescending() {
            return this.mDescending;
        }

        public int compare(AllocationInfo o1, AllocationInfo o2) {
            int diff = 0;
            switch(this.mSortMode) {
                case NUMBER:
                    diff = o1.mAllocNumber - o2.mAllocNumber;
                case SIZE:
                default:
                    break;
                case CLASS:
                    diff = o1.mAllocatedClass.compareTo(o2.mAllocatedClass);
                    break;
                case THREAD:
                    diff = o1.mThreadId - o2.mThreadId;
                    break;
                case IN_CLASS:
                    String class1 = o1.getFirstTraceClassName();
                    String class2 = o2.getFirstTraceClassName();
                    diff = compareOptionalString(class1, class2);
                    break;
                case IN_METHOD:
                    String method1 = o1.getFirstTraceMethodName();
                    String method2 = o2.getFirstTraceMethodName();
                    diff = compareOptionalString(method1, method2);
                    break;
                case ALLOCATION_SITE:
                    String desc1 = o1.getAllocationSite();
                    String desc2 = o2.getAllocationSite();
                    diff = compareOptionalString(desc1, desc2);
            }

            if (diff == 0) {
                diff = o1.mAllocationSize - o2.mAllocationSize;
            }

            if (this.mDescending) {
                diff = -diff;
            }

            return diff;
        }

        private static int compareOptionalString(String str1, String str2) {
            if (str1 != null) {
                return str2 == null ? -1 : str1.compareTo(str2);
            } else {
                return str2 == null ? 0 : 1;
            }
        }
    }

    public static enum SortMode {
        NUMBER,
        SIZE,
        CLASS,
        THREAD,
        ALLOCATION_SITE,
        IN_CLASS,
        IN_METHOD;

        private SortMode() {
        }
    }
}
