import {Component, EventEmitter, OnInit, Output, ViewChild, ElementRef, ViewEncapsulation, AfterViewInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TokenStorageService } from '../services/auth/token-storage.service';
import { NavItem } from './nav-item';
import {VERSION} from '@angular/material/core';
import {NavigationService} from '../services/navigation/navigation.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class NavigationComponent implements OnInit, AfterViewInit {

  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;

  private isMobileResolution: boolean;
  version = VERSION;

  @Output() public sidenavToggle = new EventEmitter();

  // @ts-ignore
  @ViewChild('appDrawer') appDrawer: ElementRef;
  navItems: NavItem[] = [
    {
      displayName: 'Strona główna',
      iconName: 'slideshow',
      route: '',
    },
    {
      displayName: 'Panel admin',
      iconName: 'slideshow',
      route: '/admin',
    },
    {
      displayName: 'Zarejestruj się',
      iconName: 'login',
      route: '/signup',
    },
    {
      displayName: 'Zaloguj się',
      iconName: 'login',
      route: '/login',
    },
    {
      displayName: 'Twój profil',
      iconName: 'user',
      route: '/profile',
    },
    {
      displayName: 'Wyloguj się',
      iconName: 'slideshow',
      route: '',
    },
    {
      displayName: 'Utwory',
      iconName: 'slideshow',
      route: '',
      children: [
        {
          displayName: 'Vixa',
          iconName: 'slideshow',
          route: '/tracks/vixa',
        },
        {
          displayName: 'Club',
          iconName: 'slideshow',
          route: '/tracks/club',
        },
        {
          displayName: 'Dance',
          iconName: 'slideshow',
          route: '/tracks/dance',
        },
        {
          displayName: 'Retro',
          iconName: 'slideshow',
          route: '/tracks/retro',
        },
        {
          displayName: 'Techno',
          iconName: 'slideshow',
          route: '/tracks/techno',
        },
        {
          displayName: 'Disco',
          iconName: 'slideshow',
          route: '/tracks/Disco',
        },
      ]
    },
    {
      displayName: 'Top ranking',
      iconName: 'slideshow',
      route: '',
      children: [
        {
          displayName: 'Vixa',
          iconName: 'slideshow',
          route: '/tracks/vixa',
        },
        {
          displayName: 'Club',
          iconName: 'slideshow',
          route: '/tracks/club',
        }
      ]
    },
    {
      displayName: 'Video',
      iconName: 'slideshow',
      route: '',
      children: [
        {
          displayName: 'Mixy',
          iconName: 'slideshow',
          route: '/video/category/MIX',
        },
        {
          displayName: 'Luna Mix',
          iconName: 'slideshow',
          route: '/video/category/LUNA_MIX',
        },
        {
          displayName: 'Psychopath',
          iconName: 'slideshow',
          route: '/video/category/PSYCHO_PATH',
        },
        {
          displayName: 'Retro',
          iconName: 'slideshow',
          route: '/video/category/RETRO',
        }
      ]
    }
  ];

  constructor(private http: HttpClient,
              public navigationService: NavigationService,
              private tokenStorageService: TokenStorageService) {
    this.isMobileResolution = window.innerWidth < 768;
  }

  title = 'Oskarro.com';

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  }

  public getIsMobileResolution(): boolean {
    return this.isMobileResolution;
  }

  ngAfterViewInit() {
    this.navigationService.appDrawer = this.appDrawer;
  }

}
