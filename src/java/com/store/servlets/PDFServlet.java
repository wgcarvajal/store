/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.servlets;

import com.store.controllers.util.Util;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aranda
 */
public class PDFServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String pdf = request.getParameter("pdf");
            try {
                
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(Util.BILLDIR + pdf));
               
                // Get image contents.
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                in.close();
                // Write image contents to response.
                response.setContentType("application/pdf");
                response.getOutputStream().write(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
