//
//  SecondViewController.swift
//  Lab1
//
//  Created by Karan Motreja on 2/4/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController {

    @IBAction func gotomusic(_ sender: UIButton)
    {
        if(UIApplication.shared.canOpenURL(URL(string: "spotify://")!)){
            //open the app with this URL scheme
            UIApplication.shared.open(URL(string: "spotify://")!, options: [:], completionHandler: nil)
        }else {
            if(UIApplication.shared.canOpenURL(URL(string: "music://")!)){
                UIApplication.shared.open(URL(string: "music://")!, options: [:], completionHandler: nil)
            } else {
                UIApplication.shared.open(URL(string: "http://www.apple.com/music/")!, options: [:], completionHandler: nil)
            }
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

}

