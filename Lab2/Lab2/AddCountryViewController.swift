//
//  AddCountryViewController.swift
//  Lab2
//
//  Created by Karan Motreja on 2/19/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class AddCountryViewController: UIViewController {
    
    @IBOutlet weak var countryTextfield: UITextField!
    var addedCountry = String()
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "doneSegue"{
            
            if ((countryTextfield.text?.isEmpty) == false){
                addedCountry=countryTextfield.text!
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
