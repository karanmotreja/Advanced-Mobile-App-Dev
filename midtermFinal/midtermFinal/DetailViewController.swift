//
//  DetailViewController.swift
//  midtermFinal
//
//  Created by Karan Motreja on 3/14/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit
import WebKit


class DetailViewController: UIViewController, WKNavigationDelegate {

    @IBOutlet weak var detailDescriptionLabel: UILabel!

    @IBOutlet weak var webView: WKWebView!
    
    @IBOutlet weak var webSpinner: UIActivityIndicatorView!
    
    
     var detailItem: String?
    
    func configureView() {
        // Update the user interface for the detail item.
        if let detail = detailItem {
                loadWebPage(detail.description)
            
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        webView.navigationDelegate = self
        // Do any additional setup after loading the view, typically from a nib.
        configureView()
    }

    func loadWebPage(_ urlString: String){
        
        let myurl = URL(string: urlString)
        
        let request = URLRequest(url: myurl!)
        
        webView.load(request)
    }
    
   
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
