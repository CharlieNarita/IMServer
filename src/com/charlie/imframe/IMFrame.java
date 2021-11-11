package com.charlie.imframe;

import com.charlie.imserver.service.IMServer;

/**
 * IMFrame create IMServer object to start service...
 *
 * @author AC
 * @version 1.0
 * @date 10/14/2021
 */
public class IMFrame {
    public static void main(String[] args) {
        System.out.println();
        new IMServer();
    }
}
