/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.store.servlets;

import com.store.controllers.util.GeneratePdf;
import com.store.entities.Purchase;
import com.store.entities.Purchaseitem;
import com.store.facade.PurchaseFacade;
import com.store.facade.PurchaseitemFacade;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aranda
 */
public class PDFServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
        @EJB
        private PurchaseFacade purchaseEJB;
        @EJB
        private PurchaseitemFacade purchaseitemEJB;

        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
              try {
                String pdfID = request.getParameter("pdf").split("\\.")[0];
                long id = Long.parseLong(pdfID);
                Purchase purchase = purchaseEJB.find(id);
                List<Purchaseitem> purchaseitems = purchaseitemEJB.findByPurId(id);
                GeneratePdf generatePdf = new GeneratePdf(80);
                generatePdf.generatePDF(purchase, purchaseitems);
                // Write image contents to response.
                response.setContentType("application/pdf");
                response.getOutputStream().write(generatePdf.getFicheroPdf().toByteArray());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        
   
}
