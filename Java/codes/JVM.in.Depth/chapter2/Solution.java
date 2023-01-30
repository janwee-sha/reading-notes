package chapter2;

public class Solution {
    public int maxArea(int[] height) {
        int max = 0;
        int n = height.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                max = Math.max(max, Math.min(height[i], height[j]) *(j - i));
            }
        }
        return max;
    }
}