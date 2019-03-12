//
//  FirstViewController.swift
//  Lab1
//
//  Created by Karan Motreja on 2/4/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {

    @IBOutlet weak var artistPicker: UIPickerView!
    @IBOutlet weak var choiceLabel: UILabel!
    
    let artistComponent = 0
    let albumComponent = 1
    
    var artistAlbums = ArtistAlbumsController()
    var artists = [String]()
    var albums = [String]()
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        artistAlbums.loadData()
        artists = artistAlbums.getArtists()
        albums = artistAlbums.getAlbums(index: 0)
        // Do any additional setup after loading the view, typically from a nib.
    }

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if component == artistComponent {
            return artists.count
        } else {
            return albums.count
        }
    }
    
    //Picker Delegate methods
    //returns the title for the row
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if component == artistComponent {
            return artists[row]
        } else {
            return albums[row]
        }
    }
    
    //Called when a row is selected
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        //checks which component was picked
        if component == artistComponent {
            albums = artistAlbums.getAlbums(index: row) //gets the albums for the selected artist
            artistPicker.reloadComponent(albumComponent) //reload the album component
            artistPicker.selectRow(0, inComponent: albumComponent, animated: true) //set the album component back to 0
        }
        
        let artistrow = pickerView.selectedRow(inComponent: artistComponent) //gets the selected row for the artist
        let albumrow = pickerView.selectedRow(inComponent: albumComponent) //gets the selected row for the album
        choiceLabel.text = "You like \(albums[albumrow]) by \(artists[artistrow])"
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
}

