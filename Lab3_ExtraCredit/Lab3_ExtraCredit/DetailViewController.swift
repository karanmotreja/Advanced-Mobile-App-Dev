//
//  DetailViewController.swift
//  Lab3_ExtraCredit
//
//  Created by Karan Motreja on 3/20/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit
import WebKit

class DetailViewController: UIViewController, WKNavigationDelegate {

   
    @IBOutlet weak var webView: WKWebView!
    
    @IBOutlet weak var webSpinner: UIActivityIndicatorView!
    
    func configureView() {
        
        if let detail = detailItem
        {
            loadWebPage(detail.description)
        }
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        
        webView.navigationDelegate = self
        configureView()
    }

    func loadWebPage(_ urlString: String){
        let myurl = URL(string: urlString)
        let request = URLRequest(url: myurl!)
        webView.load(request)
    }
    
    var detailItem: String? 
    
    func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
        webSpinner.startAnimating()
    }
    
    //WKNavigationDelegate method that is called when a web page loads successfully
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        webSpinner.stopAnimating()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

