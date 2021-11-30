
package model.filter;

import java.util.HashMap;
import java.util.Random;

import model.Kernel;
import model.Matrix;
import model.pixel.Pixel;

/**
 * MosaicBlur is a filter that represents a blur of the mosaic type using a 3 by 3 kernel.
 */

/**
 * An image can be "broken down" into stained glass pieces, by choosing a set of
 * points in the image (called seeds). Each pixel in the image is then paired to
 * the seed that is closest to it (by distance). This creates a cluster of
 * pixels for each seed. Then the color of each pixel in the image is replaced
 * with the average color of its cluster.
 */

public class MosaicBlur implements Filter {
  int width;
  int height;
  Matrix kernel;

  /**
   * Constructor for an mosaic geometric filter.
   * 
   * @param width  represents the width of the kernel
   * @param height represents the height of the kernel
   * @throws IllegalArgumentException if either are even or less than or equal to
   *                                  0
   */
  public MosaicBlur() throws IllegalArgumentException {
    this.width = 3;
    this.height = 3;
    this.kernel = new Matrix(width, height);
  }

  /**
   * Generates a map of pixel index to a predictable randomized boolean on whether
   * they are a cluster node.
   * 
   * @param pixels the pixels denoted.
   * @param seed   the seed to use for random.
   * @return a map of pixel index to a predictable randomized boolean on whether
   *         they are a cluster node.
   */
  private int[][] generateClusterMap(Pixel[][] pixels, int seed) {
    Random rand = new Random(seed);
    int[][] clusterMap = new int[pixels.length][pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        //if rand.boolean x3 is true, then it is a cluster node
        if (rand.nextBoolean() && rand.nextBoolean() && rand.nextBoolean()) {
          clusterMap[i][j] = 1;
        }
        else {
          clusterMap[i][j] = 0;
        }
      }
    }
    return clusterMap;
  }

  /**
   * Finds the closest cluster node to a pixel.
   * 
   * @param pixels     the pixels denoted.
   * @param clusterMap the map of pixel index to a predictable randomized boolean.
   * @param x          the x coordinate of the pixel.
   * @param y          the y coordinate of the pixel.
   * @return the closest cluster node to a pixel as an int[].
   */
  private int[] findClosestClusterNode(Pixel[][] pixels, int[][] clusterMap,
      int x, int y) {
    int[] closest = new int[2];
    int min = Integer.MAX_VALUE;
    
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        if (clusterMap[i][j] == 1) {
          int distance = Math.abs(x - i) + Math.abs(y - j);
          if (distance < min) {
            min = distance;
            closest[0] = i;
            closest[1] = j;
          }
        }
      }
    }
    return closest;
  }

  @Override
  public Pixel evaluateFilter(Pixel[][] pixels, int row, int col) {
    /**
     * will ultimately return:
     * pixels[row][col]
     * 
     * An image can be ''broken down" into such stained glass pieces, by choosing a
     * set of points in the image (called nodes). Each pixel in the image is then
     * paired to the node that is closest to it (by distance). This creates a
     * cluster of pixels for each node. Then the color of each pixel in the image is
     * replaced with the average color of its cluster.
     */
    int[] closestClusterNode = findClosestClusterNode(pixels, generateClusterMap(pixels, 420),
        row, col);
    return pixels[closestClusterNode[0]][closestClusterNode[1]];
  }

}